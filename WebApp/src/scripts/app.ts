import {Chart} from 'chart.js'

const API_URL = process.env.API_URL as string
const xlabels: string[] = []
const ytemps: number[] = []

chartIt()

async function getData(){
  const response = await fetch(API_URL)
  const data = await response.text()

  const table = data.split('\n').slice(2)

  table.forEach(row => {2 
    const cols = row.split(',')
    const year = cols[0]
    xlabels.push(year)
    const temp = cols[1]
    ytemps.push(parseFloat(temp) + 14) // Adds 14 to the graph for mean value
    console.log(year, temp)
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
        label: 'Global Average Temperature in C°, January',
        data: ytemps,
        fill:true,    // Shadow under the graph
        backgroundColor: [
          'rgba(255, 99, 132, 0.2)',
          'rgba(54, 162, 235, 0.2)',
          'rgba(255, 206, 86, 0.2)',
          'rgba(75, 192, 192, 0.2)',
          'rgba(153, 102, 255, 0.2)',
          'rgba(255, 159, 64, 0.2)',
        ],
        borderColor: [
          'rgba(255, 99, 132, 1)',
          'rgba(54, 162, 235, 1)',
          'rgba(255, 206, 86, 1)',
          'rgba(75, 192, 192, 1)',
          'rgba(153, 102, 255, 1)',
          'rgba(255, 159, 64, 1)',
        ],
        
        
        borderWidth: 2 //Thickness of the row 
      }]
    },
    options: {
      scales: {
        y: {
          ticks: {
            callback: function(value, index, values) {
              return value + '°'
            }
          }
        }
      }
    }
  })
} 



