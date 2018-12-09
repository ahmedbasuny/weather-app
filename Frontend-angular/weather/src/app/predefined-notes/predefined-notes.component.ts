import { Component, OnInit } from '@angular/core';
import { PredefinedNotesModel } from '../admin/model/Predefined-notes-model';
import { AdminService } from '../services/admin.service';

@Component({
  selector: 'app-predefined-notes',
  templateUrl: './predefined-notes.component.html',
  styleUrls: ['./predefined-notes.component.css']
})
export class PredefinedNotesComponent implements OnInit {

  form: any = {};
  preNotes: PredefinedNotesModel;
  isSetNotes = false;
  isSetNotesFailed = false;
  errorMessage = '';
  
  constructor(private adminService: AdminService) { }

  ngOnInit() {
  }

  /**
   * submit predefined notes data to save them.
   * @author Ahmed Basuny
   */
  onSubmit() {
    console.log("onSubmit function has been called.");
    console.log(this.form);

    this.preNotes = new PredefinedNotesModel(
      this.form.value1,
      this.form.value2,
      this.form.value3,
      this.form.value4,
      this.form.value5);
 
    this.adminService.setPredefinedNotes(this.preNotes).subscribe(
      data => {
        console.log(data);
        this.isSetNotes = true;
        this.isSetNotesFailed = false;
      },
      error => {
        console.log(error);
        this.errorMessage = error.error.message;
        this.isSetNotesFailed = true;
      }
    );
  }
}
