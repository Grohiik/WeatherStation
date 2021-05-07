use chrono::DateTime;
use postgres::{Client, Error, NoTls};
use serde::Serialize;
use warp::Filter;

#[derive(Serialize)]
struct Devise {
    name: String,
    created_at: DateTime<chrono::Utc>,
}

#[derive(Serialize)]
struct DataType {
    name: String,
    unit: String,
    count: i32,
    created_at: DateTime<chrono::Utc>,
}

#[derive(Serialize)]
struct Data {
    value: String,
    time: String,
}

#[tokio::main]
async fn main() {
    let list_devices = warp::path("list").and(warp::path("devices")).map(|| {
        let mut devices = Vec::new();
        fetch_devices(&mut devices).expect("blub");
        warp::reply::json(&devices)
    });

    let get_data_types = warp::path("list")
        .and(warp::path("types"))
        .and(warp::path::param())
        .map(|device_name: String| {
            let mut data_types = Vec::new();
            fetch_data_types(&mut data_types, device_name).expect("blub");
            warp::reply::json(&data_types)
        });

    let get_data = warp::path::path("data")
        .and(warp::path::param())
        .and(warp::path::param())
        .map(|device_name: String, data_type: String| {
            let mut data = Vec::new();
            fetch_data(&mut data, device_name, data_type).expect("blub");
            warp::reply::json(&data)
        });

    let routes = list_devices.or(get_data_types).or(get_data);

    warp::serve(routes).run(([127, 0, 0, 1], 3030)).await;
}

fn fetch_devices(output: &mut Vec<Devise>) -> Result<(), Error> {
    let mut client = Client::connect("redacted", NoTls)?;

    for row in client.query("SELECT device_name, created_at FROM devices", &[])? {
        let devices = Devise {
            name: row.get(0),
            created_at: row.get(1),
        };
        output.push(devices);
    }

    Ok(())
}

fn fetch_data_types(output: &mut Vec<DataType>, device: String) -> Result<(), Error> {
    let mut client = Client::connect("redacted", NoTls)?;
    let q = format!("SELECT t.name, t.unit, t.count, t.created_at FROM devices d, data_types t WHERE t.device_id = d.id and d.device_name = '{}'", device);
    for row in client.query(q.as_str(), &[])? {
        let data_types = DataType {
            name: row.get(0),
            unit: row.get(1),
            count: row.get(2),
            created_at: row.get(3),
        };
        output.push(data_types);
    }

    Ok(())
}
//"SELECT da.value, da.time FROM data_storeds da, data_types t, devices d WHERE t.id = da.type_id and t.name = '{}' and t.device_id = d.id and d.device_name = '{}'"

fn fetch_data(output: &mut Vec<Data>, device: String, datatype: String) -> Result<(), Error> {
    let mut client = Client::connect("redacted", NoTls)?;
    let q = format!("SELECT da.value, da.time FROM data_storeds da, data_types t, devices d WHERE t.id = da.type_id and t.name = '{}' and t.device_id = d.id and d.device_name = '{}'", datatype, device);
    for row in client.query(q.as_str(), &[])? {
        let data = Data {
            value: row.get(0),
            time: row.get(1),
        };
        output.push(data);
    }

    Ok(())
}
