import { Chart } from 'chart.js'

export interface ILineGraph {
  xValues: string[]
  yValues: number[]
  legend: string
}

export interface IDataset {
  data: number[]
  label: string
  borderColor: string
}

type TLineChart = Chart<'line', number[], string>

export function createLineGraph(canvas: HTMLCanvasElement): TLineChart {
  Chart.defaults.font.size = 16
  Chart.defaults.color = '#000000'
  Chart.defaults.font.family = 'Inter'
  return new Chart<'line', number[], string>(canvas, {
    type: 'line',
    data: {
      labels: [],
      datasets: [],
    },
    options: {
      elements: {
        point: {
          radius: 0,
        },
        line: {
          borderWidth: 2,
        },
      },
      plugins: {
        legend: {
          labels: {},
        },
      },
    },
  })
}

export function addChartDataset(chart: Chart, dataset: IDataset): void {
  chart.data.datasets.push(dataset)
}

export function setChartLabels(chart: Chart, labels: string[]): void {
  chart.data.labels = labels
}

export function addChartLabel(chart: Chart, label: string): void {
  chart.data.labels?.push(label)
}

export function updateChart(chart: Chart): void {
  chart.update()
}

export function clearChartData(chart: Chart): void {
  chart.data.labels = []
  chart.data.datasets = []
}
