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
    getSpecificData(currentDevice.device, currentContent.name).then((datas) => {
      currentData = datas
      // TODO: Draw to graph
      // Detta är bara en skiss på en lösning, ingen kommplet lösning
      for (let i = 0; i < currentData.length; i++) {
        IDeviceData.push({})
      }
      var subjectsData = {
        labels:Response.labels.split(','),
        datasets:IDeviceData
      }
      var options={
        scales:{
          yAxes:[{
            ticks:{
              beginAtZero:true
            },
          scaleLabel:{
            display:true,
            labelString: 'Sky',
            fontSize: 14
            }
          }]
        }
      };
    var Skycollectdata = new Chart(IDeviceData,{
      options: options
      subjectsData: Value
      
    })
        console.log(currentData)
        console.log(currentContent)
        // addChartDataset(graph, dataset)
        // updateChart(graph)
        // clearChartData(graph)
      }
    })
  }
}

window.onload = main
