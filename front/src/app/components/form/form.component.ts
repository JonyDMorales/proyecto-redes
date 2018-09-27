import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ConexionService } from '../../services/conexion.service';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.css']
})
export class FormComponent implements OnInit {

  consulta:Object = {
		ip: null,
		comunidad: null,
    oid: ".1.3.6.1.2.1.1.5.0",
    version: 1,
    usuario: null,
    auth: null,
    respuesta: null
  }

  bandera :boolean = false;
  
  constructor(public _conexionService: ConexionService) { }

  ngOnInit() {
  }

  public change(event){
    if(event == 3){
      this.bandera = true;
    } else {
      this.bandera = false;
    }
  }

  public sendData(forma: NgForm): any {
    if(forma.valid){
      this._conexionService.setSNMP(this.consulta).subscribe( res => {
        if(res['res']){
          this.consulta['respuesta'] = res['res'];
        } else {
          this.consulta['respuesta'] = "Peticion invalida";
        }
      });
    } else {
      this.consulta['respuesta'] = 'ingresa todos los valores';
    }
  }

}
