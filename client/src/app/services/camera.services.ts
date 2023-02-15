import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { firstValueFrom } from "rxjs";
import { UploadResult } from "../components/model/upload-result";

@Injectable({
    providedIn: "root"
})
export class CameraService {

    imageData = ""

    constructor(private httpClient: HttpClient) {}

    upload(form: any, image: Blob) {
        const formData = new FormData();
        formData.set("title", form['title']);
        formData.set("complain", form['complain']);
        formData.set("imageFile", image);

        return firstValueFrom(this.httpClient.post<UploadResult>("/upload-ng", formData));
    }
}