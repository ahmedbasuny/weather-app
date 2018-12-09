import { Component, OnInit } from '@angular/core';
import { WeatherService } from '../services/weather.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AdminService } from '../services/admin.service';
import { PredefinedNotesModel } from '../admin/model/Predefined-notes-model';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};


@Component({
  selector: 'app-weather',
  templateUrl: './weather.component.html',
  styleUrls: ['./weather.component.css']
})
export class WeatherComponent implements OnInit {

  ADMIN_URL = "http://localhost:8080/api/admin/note";

  city = 'Cairo';
  weather = '?';
  temp = 0;
  preNote: PredefinedNotesModel;
  definedNote = false;

  adminNote = "Aww yeah, you successfully read this important alert message.";
  predefinedNote = "Weather is Great. Have Fun !!";
  constructor(public weatherService: WeatherService, private http: HttpClient, private adminService: AdminService) {
  }

  ngOnInit() {
    this.getWeather();
    this.getAdminNote();
  }

  /**
   * get weather status
   * @author Ahmed Basuny
   */
  getWeather() {
    console.log("getWeather function has been called.");
    this.weatherService.getCurrentWeather(this.city).subscribe(x => {
      this.weather = x.weather.description;
      this.temp = x.temp;
      this.setNote(x.temp);
    });
  }

  /**
   * get admin note
   * @author Ahmed Basuny
   */
  getAdminNote() {
    console.log("getAdminNote function has been called.");
    this.http.get(this.ADMIN_URL, httpOptions).subscribe(
      data => {
        console.log(data);
        this.adminNote = data['message'];
        if (this.adminNote.length > 0 && this.adminNote != null) {
          this.definedNote = true;
        }
      },
      error => {
        console.error(`${error.status}: ${JSON.parse(error.error).message}`);
      }
    )
  }

  /**
   * set defined note based on temp degree
   * @param tempDegree 
   * @author Ahmed Basuny
   */
  setNote(tempDegree) {
    console.log("setNote function has been called.");
    console.log("setNote function params " + tempDegree);
    this.adminService.getPredefinedNotes().subscribe(
      data => {
        console.log(data);
        if (1 < tempDegree && tempDegree <= 10) {
          this.predefinedNote = data['value1'];
        } else if (10 < tempDegree && tempDegree <= 15) {
          this.predefinedNote = data['value2'];
        } else if (15 < tempDegree && tempDegree <= 20) {
          this.predefinedNote = data['value3'];
        } else if (20 < tempDegree) {
          this.predefinedNote = data['value4'];
        } else {
          this.predefinedNote = data['value5'];
        }
      },
      error => {
        console.log(error);
      }
    );
  }
}
