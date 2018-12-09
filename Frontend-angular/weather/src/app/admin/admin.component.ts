import { Component, OnInit } from '@angular/core';
import { NoteModel } from './model/note-model';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AdminService } from '../services/admin.service';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  savedSuccessfully = false;
  message: string;
  note: NoteModel;
  oldNotes: any;

  constructor(private http: HttpClient, private adminService: AdminService) { }

  ngOnInit() {
    this.getOldNotes();
  }

  /**
  * set the admin note
  * @author Ahmed Basuny
  */
  setNote() {
    console.log(" setNote Function has been called.");
    this.note = new NoteModel(this.message);
    this.adminService.setAdminNote(this.note).subscribe(
      data => {
        console.log(data);
        this.savedSuccessfully = true;
      },
      error => {
        console.error(`${error.status}: ${JSON.parse(error.error).message}`);
      }
    );
    this.getOldNotes();
  }

  /**
  * get the old notes 
  *@author Ahmed Basuny
  */
  getOldNotes() {
    console.log(" getOldNotes Function has been called.");
    this.adminService.getOldNotes().subscribe(
      data => {
        console.log(data);
        this.oldNotes = data;
      },
      error => {
        console.log(error);
      }
    )
  }

}
