import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class WeatherService {
  
  API_KEY = "47ebd0fc8b07106aa80fe498e6e86132";
  API_URL = "http://api.openweathermap.org/data/2.5/forecast?q=";
  UNIT = 'metric';
  
  constructor(private http: HttpClient) { }

  /**
   * get weather status.
   * @returns object of temp and weather description.
   * @param city 
   * @author Ahmed Basuny
   */
  getCurrentWeather(city: string): Observable<any> {
   const apiCall = `https://api.openweathermap.org/data/2.5/weather?q=${city}&units=${this.UNIT}&APPID=${this.API_KEY}`;
   console.log('apiCall', apiCall);
   return this.http.get<any>(apiCall).pipe(
     map(resp => {
       console.log('Response', resp);
       const weather = resp.weather[0];
       const temp = resp.main.temp;
       const x = { weather, temp };
       console.log(x);
       return x;
     }));
 }
}
