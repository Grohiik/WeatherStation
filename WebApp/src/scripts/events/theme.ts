// FIXME: Change to correct type for lambda function
export function addPreferColorSchemeEvent(callback: any): void {
  window
    .matchMedia('(prefers-color-scheme: dark)')
    .addEventListener('change', (e) => {
      callback(e.matches)
    })
}

export function isDarkMode(): boolean {
  return (
    window.matchMedia &&
    window.matchMedia('(prefers-color-scheme: dark)').matches
  )
}
