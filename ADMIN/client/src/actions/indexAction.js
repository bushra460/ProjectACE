import axios from "axios";

const url ="https://mcoe-webapp-projectdeltaace.azurewebsites.net/deltaace/v1/";
// const url = "http://localhost:8080/deltaace/v1/";

//***************
// Manufacturer
//***************
export function loadManufacturers(){
    return(dispatch)=>{
        return axios.get(url+"manufacturers").then((response)=> {
            console.log(response.data);
            dispatch(loadAllManufacturers(response.data))
        })
       .catch(err => alert(err));
    }
}

export function loadAllManufacturers(manufacturersData){
    console.log("loadAllManufacturers");
    console.log(manufacturersData);
    return{
        type:"GET_ALL_MANUFACTURERS",
        manufacturers:manufacturersData
    }
}

//***************
// Model
//***************
export function loadModels(){
    return(dispatch)=>{
        return axios.get(url+"models").then((response)=> {
            console.log(response.data);
            dispatch(loadAllModels(response.data))
        })
       .catch(err => alert(err));
    }
}

export function loadAllModels(modelsData){
    console.log("loadAllModels");
    console.log(modelsData);
    return{
        type:"GET_ALL_MODELS",
        models:modelsData
    }
}

//***************
// Model Year
//***************
export function loadModelYears(){
    return(dispatch)=>{
        return axios.get(url+"model-years").then((response)=> {
            console.log(response.data);
            dispatch(loadAllModelYears(response.data))
        })
       .catch(err => alert(err));
    }
}

export function loadAllModelYears(modelYearsData){
    console.log("loadAllModelYears");
    console.log(modelYearsData);
    return{
        type:"GET_ALL_MODEL_YEARS",
        modelYears:modelYearsData
    }
}

//***************
// Car
//***************
export function loadCars(){
    return(dispatch)=>{
        return axios.get(url+"cars").then((response)=> {
            console.log(response.data);
            dispatch(loadAllCars(response.data))
        })
       .catch(err => alert(err));
    }
}

export function loadAllCars(carsData){
    console.log("loadAllCars");
    console.log(carsData);
    return{
        type:"GET_ALL_CARS",
        cars:carsData
    }
}

//***************
// Car Image
//***************
export function loadCarImages(){
    return(dispatch)=>{
        return axios.get(url+"car-images").then((response)=> {
            console.log(response.data);
            dispatch(loadAllCarImages(response.data))
        })
       .catch(err => alert(err));
    }
}

export function loadAllCarImages(carImagesData){
    console.log("loadAllCarImages");
    console.log(carImagesData);
    return{
        type:"GET_ALL_CAR_IMAGES",
        carImages:carImagesData
    }
}

//***************
// Hotspot Location
//***************
export function loadHotspotLocations(){
    return(dispatch)=>{
        return axios.get(url+"hotspot-locations").then((response)=> {
            console.log(response.data);
            dispatch(loadAllHotspotLocations(response.data))
        })
       .catch(err => alert(err));
    }
}

export function loadAllHotspotLocations(hotspotLocationsData){
    console.log("loadAllHotspotLocations");
    console.log(hotspotLocationsData);
    return{
        type:"GET_ALL_HOTSPOT_LOCATIONS",
        hotspotLocations:hotspotLocationsData
    }
}

//***************
// Hotspot Detail
//***************
export function loadHotspotDetails(){
    return(dispatch)=>{
        return axios.get(url+"hotspot-details").then((response)=> {
            console.log(response.data);
            dispatch(loadAllHotspotDetails(response.data))
        })
       .catch(err => alert(err));
    }
}

export function loadAllHotspotDetails(hotspotDetailsData){
    console.log("loadAllHotspotDetails");
    console.log(hotspotDetailsData);
    return{
        type:"GET_ALL_HOTSPOT_DETAILS",
        hotspotDetails:hotspotDetailsData
    }
}