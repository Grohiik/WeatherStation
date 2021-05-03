import { getListOfDevices, getDeviceContent, getSpecificData } from './datafetch'
import { addOptionToDropdown, createCanvas, createDropdown } from './view'
import { Chart, DomPlatform } from 'chart.js'

async function main() {
  const mainView = document.querySelector('#mainView') as HTMLElement

  const dropdown = createDropdown(mainView)
  getListOfDevices()
    .then(devicelist => {
      for (let i = 0; i < devicelist.length; i++) {
        addOptionToDropdown(dropdown, devicelist[i].device)
      }

      console.log(devicelist)
    })

  const canvascontext = createCanvas(mainView)

  const dropdownA = createDropdown(mainView)
  addOptionToDropdown(dropdownA, 'Ademir')
  addOptionToDropdown(dropdownA, 'Chris')
  addOptionToDropdown(dropdownA, 'Ademir')
  addOptionToDropdown(dropdownA, 'Ademir')
}
window.onload = main
