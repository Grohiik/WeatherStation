export function createDropdown(
  parent: HTMLElement,
  defaultvalue = 'Select'
): HTMLSelectElement {
  const dropdown = document.createElement('select')
  const option = document.createElement('option')

  option.value = 'default-value'
  option.text = defaultvalue
  dropdown.appendChild(option)
  dropdown.className = 'dropdown'
  parent.appendChild(dropdown)
  return dropdown
}

export function addOptionToDropdown(
  dropdown: HTMLSelectElement,
  name: string
): void {
  const option = document.createElement('option')
  option.text = name
  dropdown.appendChild(option)
}

export function createCanvas(parent: HTMLElement): HTMLCanvasElement {
  const canvas = document.createElement('canvas')
  canvas.id = 'dataviewer'
  parent.appendChild(canvas)
  return canvas
}

export function createButton(parent: HTMLElement): HTMLButtonElement {
  const button = document.createElement('button')
  button.innerHTML = 'Remove Graph'
  button.type = 'button'
  parent.appendChild(button)
  return button
}

export function clearOptionsFromDropdrown(dropdown: HTMLSelectElement): void {
  for (let i = dropdown.length; i >= 1; i--) dropdown.remove(i)
}
