import { Routes } from '@angular/router';
import {QuestionsListComponent} from './components/questions-list/questions-list.component';

export const routes: Routes = [
  { path: '', redirectTo: 'questions', pathMatch: 'full' },
  { path: 'questions', component: QuestionsListComponent },
];
