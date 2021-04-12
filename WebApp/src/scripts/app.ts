import {Chart} from 'chart.js'

console.log('Hello, World!') // hello

const API_URL = process.env.API_URL as string
const xlabels: string[] = []
const ytemps: number[] = []


chartIt()

async function getData(){
  const response = await fetch(API_URL)
  const data = await response.text()

  const rows = data.split('\n').slice(1)

  rows.forEach(row => {
    const cols = row.split(',')
    const year = cols[1]
    xlabels.push(year)
    const temp = cols[1]
    ytemps.push(Number (temp))
    //console.log(year, temp)
  })
}

async function chartIt() {
  await getData()
  const ctx = document.getElementById('myChart') as HTMLCanvasElement
  const myChart = new Chart(ctx, {
    type: 'line',
    data: {
      labels: xlabels,
      datasets: [{
        label: 'Average temperature per year',
        data: ytemps,
        backgroundColor: [
          'rgba(255, 99, 132, 0.2)',
        ],
        borderColor: [
          'rgba(255, 99, 132, 1)',
        ],
        borderWidth: 1
      }]
    },
  })
} 

