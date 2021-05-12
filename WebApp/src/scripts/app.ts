import {
  getListOfDevices,
  getDeviceContent,
  getSpecificData,
  IDevice,
  IDeviceContent,
  IDeviceData,
} from './datafetch'
import {
  addOptionToDropdown,
  createCanvas,
  createDropdown,
  clearOptionsFromDropdrown,
} from './view'
import {
  createLineGraph,
  clearChartData,
  addChartDataset,
  setChartLabels,
  updateChart,
  IDataset,
} from './graph'
import { upperCasefirst } from './utilities'

async function main() {
  let deviceList: IDevice[] = []
  let deviceContentList: IDeviceContent[] = []

  let currentDevice: IDevice
  let currentContent: IDeviceContent
  let currentData: IDeviceData[] = []

  const mainView = document.querySelector('#mainView') as HTMLElement

  const dropdownDevice = createDropdown(mainView)
  const dropdownData = createDropdown(mainView)

  const canvas = createCanvas(mainView)
  const graph = createLineGraph(canvas)

  dropdownDevice.addEventListener('change', onDeviceSelect)
  dropdownData.addEventListener('change', onDeviceContentSelect)

  getListOfDevices().then((devices) => {
    deviceList = devices
    deviceList.forEach((current) => {
      const name = upperCasefirst(current.device)
      addOptionToDropdown(dropdownDevice, name)
    })
  })

  function onDeviceSelect() {
    currentDevice = deviceList[dropdownDevice.selectedIndex - 1]
    deviceContentList = []
    clearOptionsFromDropdrown(dropdownData)
    getDeviceContent(currentDevice.device).then((contents) => {
      deviceContentList = contents
      deviceContentList.forEach((content) => {
        const name = upperCasefirst(content.name)
        addOptionToDropdown(dropdownData, name)
      })
    })
  }

  function onDeviceContentSelect() {
    currentContent = deviceContentList[dropdownData.selectedIndex - 1]
    getSpecificData(currentDevice.device, currentContent.name).then((datas) => {
      currentData = datas
      const dataset: IDataset = {
        data: [],
        label: upperCasefirst(currentContent.name),
        borderColor: '#ff0000',
      }
      const labels: string[] = []
      clearChartData(graph)

      for (let i = 0; i < currentData.length; i++) {
        const date = new Date(Number(currentData[i].created) * 1000)
        labels.push(date.toLocaleString('se-SE', { timeZone: 'UTC' }))
        dataset.data.push(Number(currentData[i].value))
      }

      setChartLabels(graph, labels)
      addChartDataset(graph, dataset)
      updateChart(graph)
    })
  }
}

window.onload = main
