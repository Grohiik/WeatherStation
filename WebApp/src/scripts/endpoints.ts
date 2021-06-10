/*
 * Authors:
 * Gabriella Pantic, Ademir Zjajo, Christian Heisterkamp
 * Co-authors:
 * Pratchaya Khansomboom, Linnéa Mörk
 */

/*
 * 'endpoints' imports data through an API_URL from 'env' and
 * lists a path for further implementation
 */

import { API_URL } from './env'

/*
 * Lists the path for the possible devices connected to the server
 * @returns string, This returns both the API_URL and a list of devices
 */

export function listDevicesPath(): string {
  return API_URL + '/list/devices'
}

/*
 * Lists the path for the possible data types based on the list of devices
 * @returns string, This returns the API_URL and the datatypes available
 * based on the list of devices (deviceName)
 */

export function listDataTypePath(deviceName: string): string {
  return API_URL + `/list/types/${deviceName}/`
}

/*
 * Lists the path for the data based on the list of data types possible for
 * available devices
 * @returns string, This returns the API_URL and the data based on the
 * available data types that is based on the available devices (deviceName)
 */

export function listDataPath(deviceName: string, datatypes: string): string {
  return API_URL + `/data/${deviceName}/${datatypes}/`
}
