/*
 * Authors:
 * Gabriella Pantic, Ademir Zjajo, Christian Heisterkamp
 * Co-authors:
 * Pratchaya Khansomboom, Linnéa Mörk
 */

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
  createButton,
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

  const button = createButton(mainView)

  dropdownDevice.addEventListener('change', onDeviceSelect)
  dropdownData.addEventListener('change', onDeviceContentSelect)

  button.addEventListener('click', removeCanvasButton)

  function removeCanvasButton() {
    clearChartData(graph)
    canvas.style.display = 'none'
    dropdownDevice.selectedIndex = 0
    dropdownData.selectedIndex = 0
    button.style.visibility = 'hidden'
  }

  getListOfDevices().then((devices) => {
    button.style.visibility = 'hidden'
    canvas.style.display = 'none'
    deviceList = devices
    deviceList.forEach((current) => {
      const name = upperCasefirst(current.name)
      addOptionToDropdown(dropdownDevice, name)
    })
  })

  function onDeviceSelect() {
    currentDevice = deviceList[dropdownDevice.selectedIndex - 1]
    deviceContentList = []
    clearOptionsFromDropdrown(dropdownData)
    getDeviceContent(currentDevice.name).then((contents) => {
      deviceContentList = contents
      deviceContentList.forEach((content) => {
        const name = upperCasefirst(content.name)
        addOptionToDropdown(dropdownData, name)
      })
    })
  }

  function onDeviceContentSelect() {
    currentContent = deviceContentList[dropdownData.selectedIndex - 1]
    getSpecificData(currentDevice.name, currentContent.name).then((datas) => {
      currentData = datas
      const dataset: IDataset = {
        data: [],
        label: upperCasefirst(currentContent.name),
        borderColor: '#dd3e4bc7',
      }
      const labels: string[] = []
      clearChartData(graph)

      for (let i = 0; i < currentData.length; i++) {
        const date = new Date(Number(currentData[i].time) * 1000)
        labels.push(date.toLocaleString('se-SE', { timeZone: 'UTC' }))
        dataset.data.push(Number(currentData[i].value))
      }

      canvas.style.display = 'block'
      setChartLabels(graph, labels)
      addChartDataset(graph, dataset)
      updateChart(graph)
      button.style.visibility = 'visible'
    })
  }
}

window.onload = main
