package ru.kmikhails.metrolog.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Device {
   private Long id;
   private DeviceName name;
   private String regNumber;
   private String type;
   private String range;
   private String category;
   private String factoryNumber;
   private LocalDate lastInspectionDate;
   private LocalDate nextInspectionDate;
   private int inspectionFrequency;
   private InspectionPlace inspectionPlace;
   private String inspectionProtocolNumber;
   private InspectionType inspectionType;
   private DeviceLocation deviceLocation;
   private RegularCondition regularCondition;
   private MeasurementType measurementType;
   private String responsible;
   private String history;

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public DeviceName getName() {
      return name;
   }

   public void setName(DeviceName name) {
      this.name = name;
   }

   public String getRegNumber() {
      return regNumber;
   }

   public void setRegNumber(String regNumber) {
      this.regNumber = regNumber;
   }

   public String getType() {
      return type;
   }

   public void setType(String type) {
      this.type = type;
   }

   public String getRange() {
      return range;
   }

   public void setRange(String range) {
      this.range = range;
   }

   public String getCategory() {
      return category;
   }

   public void setCategory(String category) {
      this.category = category;
   }

   public String getFactoryNumber() {
      return factoryNumber;
   }

   public void setFactoryNumber(String factoryNumber) {
      this.factoryNumber = factoryNumber;
   }

   public LocalDate getLastInspectionDate() {
      return lastInspectionDate;
   }

   public void setLastInspectionDate(LocalDate lastInspectionDate) {
      this.lastInspectionDate = lastInspectionDate;
   }

   public int getInspectionFrequency() {
      return inspectionFrequency;
   }

   public void setInspectionFrequency(int inspectionFrequency) {
      this.inspectionFrequency = inspectionFrequency;
   }

   public InspectionPlace getInspectionPlace() {
      return inspectionPlace;
   }

   public void setInspectionPlace(InspectionPlace inspectionPlace) {
      this.inspectionPlace = inspectionPlace;
   }

   public String getInspectionProtocolNumber() {
      return inspectionProtocolNumber;
   }

   public void setInspectionProtocolNumber(String inspectionProtocolNumber) {
      this.inspectionProtocolNumber = inspectionProtocolNumber;
   }

   public InspectionType getInspectionType() {
      return inspectionType;
   }

   public void setInspectionType(InspectionType inspectionType) {
      this.inspectionType = inspectionType;
   }

   public DeviceLocation getDeviceLocation() {
      return deviceLocation;
   }

   public void setDeviceLocation(DeviceLocation deviceLocation) {
      this.deviceLocation = deviceLocation;
   }

   public RegularCondition getRegularCondition() {
      return regularCondition;
   }

   public void setRegularCondition(RegularCondition regularCondition) {
      this.regularCondition = regularCondition;
   }

   public MeasurementType getMeasurementType() {
      return measurementType;
   }

   public void setMeasurementType(MeasurementType measurementType) {
      this.measurementType = measurementType;
   }

   public String getResponsible() {
      return responsible;
   }

   public void setResponsible(String responsible) {
      this.responsible = responsible;
   }

   public LocalDate getNextInspectionDate() {
      return nextInspectionDate;
   }

   public void setNextInspectionDate(LocalDate nextInspectionDate) {
      this.nextInspectionDate = nextInspectionDate;
   }

   public String getHistory() {
      return history;
   }

   public void setHistory(String history) {
      this.history = history;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Device device = (Device) o;
      return inspectionFrequency ==
              device.inspectionFrequency &&
              Objects.equals(id, device.id) &&
              Objects.equals(name, device.name) &&
              Objects.equals(regNumber, device.regNumber) &&
              Objects.equals(type, device.type) &&
              Objects.equals(range, device.range) &&
              Objects.equals(category, device.category) &&
              Objects.equals(factoryNumber, device.factoryNumber) &&
              Objects.equals(lastInspectionDate, device.lastInspectionDate) &&
              Objects.equals(nextInspectionDate, device.nextInspectionDate) &&
              Objects.equals(inspectionPlace, device.inspectionPlace) &&
              Objects.equals(inspectionProtocolNumber, device.inspectionProtocolNumber) &&
              Objects.equals(inspectionType, device.inspectionType) &&
              Objects.equals(deviceLocation, device.deviceLocation) &&
              Objects.equals(regularCondition, device.regularCondition) &&
              Objects.equals(measurementType, device.measurementType) &&
              Objects.equals(responsible, device.responsible) &&
              Objects.equals(history, device.history);
   }

   @Override
   public int hashCode() {
      return Objects.hash(id, name, regNumber, type, range, category, factoryNumber, lastInspectionDate,
              inspectionFrequency, inspectionPlace, inspectionProtocolNumber, inspectionType,
              deviceLocation, regularCondition, measurementType, responsible, lastInspectionDate, history);
   }

   @Override
   public String toString() {
      return "Device{" +
              "id=" + id +
              ", name='" + name + '\'' +
              ", regNumber='" + regNumber + '\'' +
              ", type='" + type + '\'' +
              ", range='" + range + '\'' +
              ", category='" + category + '\'' +
              ", factoryNumber='" + factoryNumber + '\'' +
              ", lastInspectionDate=" + lastInspectionDate +
              ", nextInspectionDate=" + nextInspectionDate +
              ", inspectionFrequency=" + inspectionFrequency +
              ", inspectionPlace=" + inspectionPlace +
              ", inspectionProtocolNumber='" + inspectionProtocolNumber + '\'' +
              ", inspectionType=" + inspectionType +
              ", deviceLocation=" + deviceLocation +
              ", regularCondition=" + regularCondition +
              ", measurementType=" + measurementType +
              ", responsible=" + responsible +
              ", history='" + history + '\'' +
              '}';
   }
}
