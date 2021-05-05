import {
  getListOfDevices,
  getDeviceContent,
  getSpecificData,
  IDevice,
  IDeviceContent,
  IDeviceData
} from './datafetch'
import {
  addOptionToDropdown,
  createCanvas,
  createDropdown,
  clearOptionsFromDropdrown
} from './view'
import { createLineGraph } from './graph'

async function main() {
  let deviceList: IDevice[] = []
  let deviceContentList: IDeviceContent[] = []

  let currentDevice: IDevice
  let currentContent: IDeviceContent
  let currentData: IDeviceData[] = []

  const mainView = document.querySelector('#mainView') as HTMLElement
  const canvas = createCanvas(mainView)

  const dropdownDevice = createDropdown(mainView)
  const dropdownData = createDropdown(mainView)
  dropdownDevice.addEventListener('change', onDeviceSelect)
  dropdownData.addEventListener('change', onDeviceContentSelect)

  getListOfDevices()
    .then(devices => {
      deviceList = devices
      deviceList.forEach(current => {
        addOptionToDropdown(dropdownDevice, current.device)
      })
    })

  function onDeviceSelect() {
    currentDevice = deviceList[dropdownDevice.selectedIndex -1]
    deviceContentList = []
    clearOptionsFromDropdrown(dropdownData)
    getDeviceContent(currentDevice.device)
      .then(contents => {
        deviceContentList = contents
        deviceContentList.forEach(content => {
          addOptionToDropdown(dropdownData, content.name)
        })
      })
  }

  function onDeviceContentSelect() {
    currentContent = deviceContentList[dropdownData.selectedIndex]
    getSpecificData(currentDevice.device, currentContent.name)
      .then(datas => {
        currentData = datas
        // TODO: Draw to graph
      })
  }

}

window.onload = main
