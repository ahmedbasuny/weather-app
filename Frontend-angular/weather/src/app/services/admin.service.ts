import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { NoteModel } from '../admin/model/note-model';
import { Observable } from 'rxjs';
import { PredefinedNotesModel } from '../admin/model/Predefined-notes-model';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  ADMIN_URL = "http://localhost:8080/api/admin/note";
  ADMIN_URL_OLD_NOTES = "http://localhost:8080/api/admin/notes";
  ADMIN_PRE_NOTES = "http://localhost:8080/api/predefined/notes";

  constructor(private http: HttpClient) { }

  /**
   * set admin note
   * @param note 
   * @author Ahmed Basuny
   */
  setAdminNote(note: NoteModel): Observable<string> {
    return this.http.post<string>(this.ADMIN_URL, note, httpOptions);
  }

  /**
   * set predefined notes
   * @param notes 
   * @author Ahmed Basuny
   */
  setPredefinedNotes(notes: PredefinedNotesModel): Observable<PredefinedNotesModel> {
    return this.http.post<PredefinedNotesModel>(this.ADMIN_PRE_NOTES, notes, httpOptions);
  }

  /**
   * get predefined notes
   * @author Ahmed Basuny
   */
  getPredefinedNotes() {
    console.log(this.http.get<PredefinedNotesModel>(this.ADMIN_PRE_NOTES, httpOptions))
    return this.http.get<PredefinedNotesModel>(this.ADMIN_PRE_NOTES, httpOptions);
  }

  /**
   * get old notes
   * @author Ahmed Basuny
   */
  getOldNotes() {
    return this.http.get(this.ADMIN_URL_OLD_NOTES, httpOptions);
  }
}
