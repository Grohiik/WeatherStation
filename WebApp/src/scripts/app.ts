import { Chart } from 'chart.js'

const API_URL = process.env.API_URL as string
const xlabels: string[] = []
const ytemps: number[] = []

/*
async function getData() {
  const response = await fetch(API_URL)
  const data = await response.json()
  console.log(data)
} */

chartIt()

async function getData(){
  let response = await fetch(API_URL)
  let data = await response.json()

  console.log(data[0])

  for(var i = 0; i < data.length; i++) {
    let datapoint = data[i]

    let date = (new Date(datapoint.time * 1000)).toLocaleTimeString();
    xlabels.push(date)
    ytemps.push(parseFloat(datapoint.temperature))   
    console.log(datapoint)
  }
}

async function chartIt() {
  await getData()
  const ctx = document.getElementById('myChart') as HTMLCanvasElement
  const myChart = new Chart(ctx, {
    type: 'line',
    data: {
      labels: xlabels,
      datasets: [{
        label: 'Temperature from weather station',
        data: ytemps,
        fill:true,    // Shadow under the graph
        backgroundColor:  [
          'rgba(255, 99, 132, 0.2)',
        ],
        borderColor:  [
          'rgba(255, 99, 132, 1)',
        ],

        borderWidth: 2 // Thickness of the row
      }]
    },
    options: {
      elements: {
        point:{
          radius: 0
        }
      },
      scales: {
        y: {
          ticks: {
            callback: function(value, index, values) {
              return value + '°'
            }
          }
        }
      }
    }
  })
}
