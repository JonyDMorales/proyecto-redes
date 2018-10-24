import { Component, OnInit } from '@angular/core';
import { ConexionService } from '../../services/conexion.service';

@Component({
  selector: 'app-grafica',
  templateUrl: './grafica.component.html',
  styleUrls: ['./grafica.component.css']
})
export class GraficaComponent implements OnInit {

  nombre :string = 'linux: antoniorf-NV56R';
  nombre2 :string = 'router';
  contacto :string = 'Me <me@example.org>';
  contacto2 :string = 'antonio@hotmail.com';
  
  public lineChartSysRAMUsed:Array<any> = [ {}, {}, {}];
  public lineChartLabels:Array<any> = [];

  constructor(private _conexionService: ConexionService) {
    this.getInformation();
    //setTimeout(this.getInformation(), 10000);
  }

  getInformation(){
    this.lineChartSysRAMUsed = [ {}, {}, {}];
    this.lineChartLabels = [];
    this._conexionService.getSNMP().subscribe(res => {
      let _line:Array<any> = new Array();
      let sysRAMUsed :Array<any> = [];
      let sysRAMFree :Array<any> = [];
      let SysCPU :Array<any> = [];
      
      for(let document in res['docs']){
        let doc = res['docs'][document]
        
        if(doc['pc'] == 'linux'){
          if(doc['key'] == 'SysRAMUsed'){
            sysRAMUsed.push(doc['respuesta']);
          } else if(doc['key'] == 'SysRAMFree'){
            sysRAMFree.push(doc['respuesta']);
          }else if(doc['key'] == 'SysCPU'){
            SysCPU.push(doc['respuesta']);
            this.lineChartLabels.push(doc['created_at']);
          }
        }
      }
      _line[0] = {data: sysRAMUsed, label: 'sysRAMUsed'};
      _line[1] = {data: sysRAMFree, label: 'sysRAMFree'};
      _line[2] = {data: SysCPU, label: 'SysCPU'};
      
      this.lineChartSysRAMUsed = _line;
    });
  }

  ngOnInit() {
  }
  
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
