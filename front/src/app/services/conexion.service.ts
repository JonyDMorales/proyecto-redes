import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { NgForm } from '@angular/forms';

@Injectable()
export class ConexionService {

  constructor(private _httpClient: HttpClient) { }

  public getSNMP(){
    let url: string = 'http://localhost:8080/back/app/snmp/get';
    return this._httpClient.post(url, { }, { headers: new HttpHeaders().set("Access-Control-Allow-Origin", "*") });   
  }

  public setSNMP(json){
    let url: string = 'http://localhost:8080/back/app/snmp/insert';
    return this._httpClient.post(url, 
        { ip: json['ip'], comunidad: json['comunidad'], oid: json['oid'], version: json['version'], key: "otro" }, 
        { headers: new HttpHeaders().set('Content-Type', 'application/json') });
  }

}
