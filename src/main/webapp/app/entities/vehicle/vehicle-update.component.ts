import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IVehicle, Vehicle } from 'app/shared/model/vehicle.model';
import { VehicleService } from './vehicle.service';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer/customer.service';

@Component({
  selector: 'jhi-vehicle-update',
  templateUrl: './vehicle-update.component.html',
})
export class VehicleUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    vehicleId: [],
    type: [],
    classOfVehicle: [],
    typeOfBody: [],
    colour: [],
    numberOfCylinders: [],
    engineNumber: [],
    horsePower: [],
    cubicCapacity: [],
    noOfStandee: [],
    unladenWeight: [],
    prevRegnNo: [],
    makersName: [],
    makersCountry: [],
    yearsOfManufacture: [],
    chassisNumber: [],
    fuelUsed: [],
    rpm: [],
    seats: [],
    wheelBase: [],
    maxLaden: [],
  });

  constructor(protected vehicleService: VehicleService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vehicle }) => {
      this.updateForm(vehicle);
    });
  }

  updateForm(vehicle: IVehicle): void {
    this.editForm.patchValue({
      id: vehicle.id,
      name: vehicle.name,
      vehicleId: vehicle.vehicleId,
      type: vehicle.type,
      classOfVehicle: vehicle.classOfVehicle,
      typeOfBody: vehicle.typeOfBody,
      colour: vehicle.colour,
      numberOfCylinders: vehicle.numberOfCylinders,
      engineNumber: vehicle.engineNumber,
      horsePower: vehicle.horsePower,
      cubicCapacity: vehicle.cubicCapacity,
      noOfStandee: vehicle.noOfStandee,
      unladenWeight: vehicle.unladenWeight,
      prevRegnNo: vehicle.prevRegnNo,
      makersName: vehicle.makersName,
      makersCountry: vehicle.makersCountry,
      yearsOfManufacture: vehicle.yearsOfManufacture,
      chassisNumber: vehicle.chassisNumber,
      fuelUsed: vehicle.fuelUsed,
      rpm: vehicle.rpm,
      seats: vehicle.seats,
      wheelBase: vehicle.wheelBase,
      maxLaden: vehicle.maxLaden,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const vehicle = this.createFromForm();
    if (vehicle.id !== undefined) {
      this.subscribeToSaveResponse(this.vehicleService.update(vehicle));
    } else {
      this.subscribeToSaveResponse(this.vehicleService.create(vehicle));
    }
  }

  private createFromForm(): IVehicle {
    return {
      ...new Vehicle(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      vehicleId: this.editForm.get(['vehicleId'])!.value,
      type: this.editForm.get(['type'])!.value,
      classOfVehicle: this.editForm.get(['classOfVehicle'])!.value,
      typeOfBody: this.editForm.get(['typeOfBody'])!.value,
      colour: this.editForm.get(['colour'])!.value,
      numberOfCylinders: this.editForm.get(['numberOfCylinders'])!.value,
      engineNumber: this.editForm.get(['engineNumber'])!.value,
      horsePower: this.editForm.get(['horsePower'])!.value,
      cubicCapacity: this.editForm.get(['cubicCapacity'])!.value,
      noOfStandee: this.editForm.get(['noOfStandee'])!.value,
      unladenWeight: this.editForm.get(['unladenWeight'])!.value,
      prevRegnNo: this.editForm.get(['prevRegnNo'])!.value,
      makersName: this.editForm.get(['makersName'])!.value,
      makersCountry: this.editForm.get(['makersCountry'])!.value,
      yearsOfManufacture: this.editForm.get(['yearsOfManufacture'])!.value,
      chassisNumber: this.editForm.get(['chassisNumber'])!.value,
      fuelUsed: this.editForm.get(['fuelUsed'])!.value,
      rpm: this.editForm.get(['rpm'])!.value,
      seats: this.editForm.get(['seats'])!.value,
      wheelBase: this.editForm.get(['wheelBase'])!.value,
      maxLaden: this.editForm.get(['maxLaden'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVehicle>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
