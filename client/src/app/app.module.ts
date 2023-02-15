import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CameraService } from './services/camera.services';
import { CameraComponentComponent } from './components/camera-component.component';
import { UploadComponentComponent } from './components/upload-component.component';
import { WebcamModule } from 'ngx-webcam';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './material.module';


@NgModule({
  declarations: [
    AppComponent,
    CameraComponentComponent,
    UploadComponentComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    WebcamModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MaterialModule,
    

  ],
  providers: [CameraService],
  bootstrap: [AppComponent]
})
export class AppModule { }
