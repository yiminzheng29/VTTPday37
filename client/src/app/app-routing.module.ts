import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CameraComponentComponent } from './components/camera-component.component';
import { UploadComponentComponent } from './components/upload-component.component';

const routes: Routes = [
  {path:'', component: CameraComponentComponent},
  {path:'upload', component:UploadComponentComponent},
  {path: '**', redirectTo:'/', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
