export function createCanvas(parent:HTMLElement)
: HTMLCanvasElement {
  const canvas = document.createElement('canvas')
  canvas.id = 'dataviewer'
  parent.appendChild(canvas)
  return canvas
}

export function createDropdown(parent:HTMLElement,
  defaultvalue = 'select'): HTMLSelectElement {
  const dropdown = document.createElement('select')
  const option = document.createElement('option')
  option.value = 'default-value'
  option.text = defaultvalue
  dropdown.appendChild(option)
  dropdown.className = 'dropdown'
  parent.appendChild(dropdown)
  return dropdown
}

export function addOptionToDropdown(dropdown:HTMLSelectElement,
  name: string): void {
  const option = document.createElement('option')
  option.text = name
  dropdown.appendChild(option)
}

export function clearOptionsFromDropdrown(dropdown:HTMLSelectElement): void {
  dropdown.innerHTML = ''
}

