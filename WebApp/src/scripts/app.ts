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
  addChartLabel,
  setChartLabels,
  updateChart,
  IDataset,
} from './graph'
import { Chart } from 'chart.js'

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

  const dataset = {
    data: [0, 3, 5, 6, 7],
    label: 'inte ademir',
    borderColor: '#ff0f0f',
  }
  const dataset2 = {
    data: [1, 9, -1, 6],
    label: 'christian',
    borderColor: '#ffff00',
  }
  const dataset3 = {
    data: [-19, 1.54, -33, 6],
    label: 'send help',
    borderColor: '#00ff00',
  }
  addChartLabel(graph, '1')
  addChartLabel(graph, '2')
  addChartLabel(graph, '3')
  addChartLabel(graph, '4')
  // addChartDataset(graph, dataset)
  // addChartDataset(graph, dataset2)
  // addChartDataset(graph, dataset3)

  dropdownDevice.addEventListener('change', onDeviceSelect)
  dropdownData.addEventListener('change', onDeviceContentSelect)

  getListOfDevices().then((devices) => {
    deviceList = devices
    deviceList.forEach((current) => {
      addOptionToDropdown(dropdownDevice, current.device)
    })
  })

  function onDeviceSelect() {
    currentDevice = deviceList[dropdownDevice.selectedIndex - 1]
    deviceContentList = []
    clearOptionsFromDropdrown(dropdownData)
    getDeviceContent(currentDevice.device).then((contents) => {
      deviceContentList = contents
      deviceContentList.forEach((content) => {
        addOptionToDropdown(dropdownData, content.name)
      })
    })
  }

  function onDeviceContentSelect() {
    currentContent = deviceContentList[dropdownData.selectedIndex - 1]
    getSpecificData(currentDevice.device, currentContent.name)
      .then(datas => {
        currentData = datas
        const dataset: IDataset = {
          data: [],
          label: '',
          borderColor: '#ff0000'
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
