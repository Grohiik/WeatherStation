import { API_URL } from './env'

export function listDevicesPath(): string {
  return API_URL + '/ListDevices'
}

export function listDataTypePath(deviceName: string): string {
  return API_URL + `/${deviceName}/datatypes`
}

export function listDataPath(deviceName: string, datatypes: string): string {
  return API_URL + `/${deviceName}/${datatypes}/data`
}
