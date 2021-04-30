import { API_URL } from './env'

export function listDevicesPath(): string {
  // TODO change to the correct device path when posible
  return API_URL + '/getdevices'
}

export function listDataTypePath(deviceName: string): string {
  return API_URL + `/${deviceName}/datatypes`
}

export function listDataPath(deviceName: string, datatypes: string): string {
  return API_URL + `/${deviceName}/${datatypes}/data`
}
