import { Chart } from 'chart.js'

export interface ILineGraph {
  xValues: string[]
  yValues: number[]
  legend: string
}

export function createLineGraph(canvas: HTMLCanvasElement)
  : Chart<'line', string[], number> {
  return new Chart<'line', string[], number>(
    canvas, {
      type: 'line',
      data: {
        labels: [],
        datasets: [],
      },
    }
  )
}

export function setLineData(chart: Chart<'line', string[], number>,
  data: ILineGraph) {
  // TODO: Set the data into the graph
}

export function updateGraph(chart: Chart) {
  // TODO: Update graph
  chart.update()
  updateChart(chart:Chart):void {
    
  }
}

export function clearData(chart: Chart): void {
  // Clears the datachart
  chart.data.labels = []
  chart.data.datasets = []
}
