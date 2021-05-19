/**
 * 'datafetch' imports the path for the collected data from the API_URL
 * from 'endpoints' and implements these into interfaces for further
 * implementation
 */

import { listDataPath, listDataTypePath, listDevicesPath } from './endpoints'

/**
 * Interface for the devices connected to the server
 */

export interface IDevice {
  device: string
  description: string
}

/**
 * Interface for the content of the devices connected to the server
 */

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
      .catch((error) => {
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
