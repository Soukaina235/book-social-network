import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent implements OnInit{

  ngOnInit() {
    // all the elements with the 'nav-link' class
    const linkColor = document.querySelectorAll('.nav-link');
    linkColor.forEach(link => {
      // window.location.href contains the full URL
      if (window.location.href.endsWith(link.getAttribute('href') || '')) { // '' if link.getAttribute('href') is null
        link.classList.add('active');
      }
      link.addEventListener('click', (event) => {
        linkColor.forEach(l => {
          l.classList.remove('active');
        })
        link.classList.add('active');
      })
    })
  }

  logout() {
    localStorage.removeItem('token');
    window.location.reload();
  }
}
