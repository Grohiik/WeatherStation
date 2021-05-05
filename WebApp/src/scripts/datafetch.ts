import { listDataPath, listDataTypePath, listDevicesPath } from './endpoints'

export interface IDevice {
  device: string
  description: string
}

export interface IDeviceContent {
  name: string
  
  type: string
  unit: string
  count: number
}

export interface IDeviceData {
  value: string
  created: string
}

export async function getListOfDevices(): Promise<IDevice[]> {
  return new Promise<IDevice[]>((resolve, rejects) => {
    fetch(listDevicesPath())
      .then((response) => {
        return response.json()
      })
      .then((value) => {
        resolve(value as IDevice[])
      })
      .catch((error) => {
        resolve([])
      })
  })
}

export async function getDeviceContent(
  deviceName: string
): Promise<IDeviceContent[]> {
  return new Promise<IDeviceContent[]>((resolve, rejects) => {
    fetch(listDataTypePath(deviceName))
      .then((response) => {
        return response.json()
      })
      .then((value) => {
        resolve(value as IDeviceContent[])
      })
      .catch ((error) => {
        resolve([])
      })
  })
}

export async function getSpecificData(
  deviceName: string,
  datatypes: string
): Promise<IDeviceData[]> {
  return new Promise<IDeviceData[]>((resolve, rejects) => {
    fetch(listDataPath(deviceName, datatypes))
      .then((response) => {
        return response.json()
      })
      .then((value) => {
        resolve(value as IDeviceData[])
      })
      .catch((error) => {
        resolve([])
      })
  })
}
