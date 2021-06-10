/*
 * Authors:
 * Gabriella Pantic, Ademir Zjajo, Christian Heisterkamp
 * Co-authors:
 * Pratchaya Khansomboom, Linnéa Mörk
 */

export function upperCasefirst(string: string): string {
  return string.charAt(0).toUpperCase() + string.slice(1)
}
