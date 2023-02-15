import { AfterViewInit, Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { WebcamComponent, WebcamImage } from 'ngx-webcam';
import { Subject, Subscription } from 'rxjs';
import { CameraService } from '../services/camera.services';

@Component({
  selector: 'app-camera-component',
  templateUrl: './camera-component.component.html',
  styleUrls: ['./camera-component.component.css']
})
export class CameraComponentComponent implements OnInit, OnDestroy, AfterViewInit{

  @ViewChild(WebcamComponent)
  webcam!: WebcamComponent
  width = 400;
  height = 400;
  pics: string[] = []
  sub$!: Subscription
  trigger = new Subject<void>;

  constructor(private router: Router, private cameraSvc: CameraService) {}

  ngOnInit(): void {
      console.log("init..." + this.webcam)
  }

  ngOnDestroy(): void {
      
  }

  ngAfterViewInit(): void {
      this.webcam.trigger = this.trigger;
      this.sub$ = this.webcam.imageCapture.subscribe(
        this.snapshot.bind(this)
      )
  }

  snap(){
    this.trigger.next()
  }

  snapshot(webcamImg: WebcamImage) {
    this.cameraSvc.imageData = webcamImg.imageAsDataUrl;
    this.pics.push(webcamImg.imageAsDataUrl);
  }

}
