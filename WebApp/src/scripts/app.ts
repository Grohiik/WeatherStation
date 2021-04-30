import { getListOfDevices } from './datafetch'
import { addOptionToDropdown, createCanvas, createDropdown } from './view'

async function main() {
  getListOfDevices()
    .then(devicelist=>{
      console.log(devicelist)
    })
  const mainView = document.querySelector('#mainView') as HTMLElement
  const canvascontext = createCanvas(mainView)

  const dropdown = createDropdown(mainView)
  addOptionToDropdown(dropdown, 'Ademir')
  addOptionToDropdown(dropdown, 'Ademir')
  addOptionToDropdown(dropdown, 'Ademir')
  addOptionToDropdown(dropdown, 'Ademir')
  const dropdownA = createDropdown(mainView)
  const dropdownB = createDropdown(mainView)
  const dropdownC = createDropdown(mainView)
  const dropdownD = createDropdown(mainView)
}


window.onload = main
