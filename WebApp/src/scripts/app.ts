import {Chart} from 'chart.js'

const API_URL = process.env.API_URL as string
const xlabels: string[] = []
const ytemps: number[] = []

chartIt()

async function getData(){
  const response = await fetch(API_URL)
  const data = await response.text()

  const table = data.split('\n').slice(2)

  rows.forEach(row => {2 
    const cols = row.split(',')
    const year = cols[0]
    xlabels.push(year)
    const temp = cols[1]
    //ytemps.push(Number(temp)) // detta är från 0 linjen 
    ytemps.push(parseFloat(temp) + 14) // lägger till 14 för att få bättre graff
    console.log(year, temp)
  })
}

async function chartIt() {
  await getData()
  const ctx = document.getElementById('myChart') as HTMLCanvasElement
  const myChart = new Chart(ctx, {
    type: 'line',
    data: {
      //labels: ['1','2','3','4','5'],
      labels: xlabels,
      datasets: [{
        label: 'Global Average Temperature in C°, January',
        data: ytemps,
        fill:true,    // detta är skuggan under grafe 
        backgroundColor: [
          'rgba(255, 99, 132, 0.2)',
          'rgba(54, 162, 235, 0.2)',
          'rgba(255, 206, 86, 0.2)',
          'rgba(75, 192, 192, 0.2)',
          'rgba(153, 102, 255, 0.2)',
          'rgba(255, 159, 64, 0.2)',        ],
        borderColor: [
          'rgba(255, 99, 132, 1)',
                'rgba(54, 162, 235, 1)',
                'rgba(255, 206, 86, 1)',
                'rgba(75, 192, 192, 1)',
                'rgba(153, 102, 255, 1)',
                'rgba(255, 159, 64, 1)',
        ],
        borderWidth: 2 // tjokleken på linjen 
      }]
    },
    options: {
      scales: {
          y: {
              ticks: {
                  // Include a dollar sign in the ticks
                  callback: function(value, index, values) {
                      return value + '°';
                  }
              }
          }
      }
  }
  })
} 



