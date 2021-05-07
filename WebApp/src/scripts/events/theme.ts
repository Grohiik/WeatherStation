export function addPreferColorSchemeEvent(
  callback: (isDark: boolean) => void
): void {
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
