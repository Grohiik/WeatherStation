import { listDevicesPath } from './endpoints'

interface IDevice{
  device: string
  description: string
}

interface IDeviceContent{
  name: string
  type: string
  unit: string
  count: number
}

interface IDeviceData{
  value: string
  created: string
}

export async function getListOfDevices():Promise<IDevice[]> {
  return new Promise<IDevice[]>((resolve, reject) => {
    fetch(listDevicesPath())
      .then(response=>{return response.json()})
      .then(value=>{
        resolve(value as IDevice[])
      })
      .catch(error=>{
        resolve([])
      })
  })
}

export async function getDeviceContent(
  deviceName: string): Promise<IDeviceContent[]> {
  return new Promise<IDeviceContent[]>((resolve, rejects) => {
    // TODO make a funktion that gets device content
    resolve([])
  })

}

export async function getSpecificData(
  deviceName: string, datatypes: string): Promise<IDeviceData[]> {
  return new Promise<IDeviceData[]>((resolve, rejects) => {
    // TODO make a funktion that gets specifick data from the specifick device
    resolve([])
  })
}
