import { Component, OnInit } from '@angular/core';
import { ConexionService } from '../../services/conexion.service';

@Component({
  selector: 'app-grafica',
  templateUrl: './grafica.component.html',
  styleUrls: ['./grafica.component.css']
})
export class GraficaComponent implements OnInit {

  nombre :string = 'linux';
  nombre2 :string = 'linux 2';;
  nombre3 :string = 'router';;
  contacto :string = 'jonatan_moralest@hotmail.com';;
  contacto2 :string = 'antonio@hotmail.com';;
  contacto3 :string = 'otro@hotmail.com';;
  
  constructor(private _conexionService: ConexionService) {
    this._conexionService.getSNMP().subscribe(res => {
      
      for(let document in res['docs']){
        let doc = res['docs'][document]
        if(doc['pc'] == 'linux'){
          if(doc['key'] == 'procesador')
            console.log(doc['respuesta']);
        }
      }
    });
  }

  ngOnInit() {
  }

  public lineChartData:Array<any> = [
    { data: [65, 59, 80, 81, 90], label: 'Procesador' },
    { data: [28, 48, 40, 19, 100], label: 'Disco' },
    { data: [18, 48, 77, 9, 10], label: 'RAM' },
  ];

  public lineChartLabels:Array<any> = ['Lunes', 'Martes', 'Miercoles', 'Jueves', 'Viernes'];
  
  public lineChartOptions:any = {
    responsive: true
  };

  public lineChartColors:Array<any> = [
    {
      backgroundColor: 'rgba(148,159,177,0)',
      borderColor: 'rgba(204, 0, 0, 1)',
      pointBackgroundColor: 'rgba(175, 29, 29, 1)',
      pointBorderColor: 'rgba(175, 29, 29, 1)',
      pointHoverBackgroundColor: 'rgba(0, 0, 0, 1)',
      pointHoverBorderColor: 'rgba(0, 0, 0, 1)'
    },
    {
      backgroundColor: 'rgba(148,159,177,0)',
      borderColor: 'rgba(60, 52, 197, 1)',
      pointBackgroundColor: 'rgba(127, 122, 220, 1)',
      pointBorderColor: 'rgba(127, 122, 220, 1)',
      pointHoverBackgroundColor: 'rgba(0, 0, 0, 1)',
      pointHoverBorderColor: 'rgba(0, 0, 0, 1)'
    },
    {
      backgroundColor: 'rgba(148,159,177,0)',
      borderColor: 'rgba(52, 173, 46, 1)',
      pointBackgroundColor: 'rgba(135, 222, 130, 1)',
      pointBorderColor: 'rgba(135, 222, 130, 1)',
      pointHoverBackgroundColor: 'rgba(0, 0, 0, 1)',
      pointHoverBorderColor: 'rgba(0, 0, 0, 1)'
    }
  ];

  public lineChartLegend:boolean = true;
  public lineChartType:string = 'line';
 
}
