import { Chart, DomPlatform } from 'chart.js'

import { API_URL } from './env'
import { isDarkMode, addPreferColorSchemeEvent } from './events/theme'

async function main() {
  addPreferColorSchemeEvent((e: any) => {
    // Makes it possible to change theme, darkmode or lightmode
    console.log('is dark mode: ' + e)
  })

  const canvasContext = document.querySelector(
    '#graph-display'
  ) as HTMLCanvasElement

  const dataChart = new Chart(canvasContext, {
    type: 'line',
    data: {
      labels: [''],
      datasets: [],
    },
  })

  const dropDownDevices = document.getElementById(
    'selectDevice'
  ) as HTMLSelectElement
  const dropDownDataList = document.getElementById(
    'selectData'
  ) as HTMLSelectElement

  // TODO: Use new the new path instead of /getdevices
  const deviceList = await fetch(API_URL + '/getdevices').then((res) =>
    res.json()
  )

  deviceList.forEach((el: any) => {
    const opt = document.createElement('option')
    opt.text = el.device
    dropDownDevices.appendChild(opt)
  })

  // TODO: Add event listeners when user select
  //       an item from the dropdown menu for devices

  dropDownDevices.onchange = async (e) => {
    const index = dropDownDevices.options.selectedIndex

    const deviceDatas = await fetch(
      API_URL + '/findbydevice?input=' + dropDownDevices.options[index].text
    ).then((res) => res.json())

    clearData(dataChart)

    const labels: string[] = []
    const data: number[] = []
    for (let i = 0; i < deviceDatas.length; i++) {
      data.push(deviceDatas[i].temperature)
      labels.push(new Date(deviceDatas[i].time * 1000).toLocaleTimeString())
    }
    dataChart.data.labels = labels
    dataChart.data.datasets.push({
      data,
      label: 'temperature',
      borderColor: '#ff776e',
    })

    updateChart(dataChart)
  }
}

function updateChart(chart: Chart): void {
  // Updates chart
  chart.update()
}

function clearData(chart: Chart): void {
  // Clears datachart
  chart.data.labels = []
  chart.data.datasets = []
}

window.onload = main
