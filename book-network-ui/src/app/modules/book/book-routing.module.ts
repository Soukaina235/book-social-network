import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {MainComponent} from "./pages/main/main.component";
import {BookListComponent} from "./pages/book-list/book-list.component";
import {MyBooksComponent} from "./pages/my-books/my-books.component";
import {ManageBookComponent} from "./pages/manage-book/manage-book.component";

// all routes are preceded by /books
const routes: Routes = [
  {
    path: '', // /books
    component: MainComponent,
    children: [
      {
        path: '',
        component: BookListComponent
      },
      {
        path: 'my-books',
        component: MyBooksComponent
      },
      {
        path: 'manage',
        component: ManageBookComponent
      },
      {
        path: 'manage/:bookId', // edit book
        component: ManageBookComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BookRoutingModule { }
