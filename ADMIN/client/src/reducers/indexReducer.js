let defaultState={
    manufacturers: [],
    models: [],
    modelYears: [],
    cars: [],
    carImages: [],
    hotspotLocations: [],
    hotspotDetails: []
}

const mainReducer=(state=defaultState,action)=>{
// const mainReducer=(state,action)=>{
    console.log("Inside reducer")
    console.log(action)
    if(action.type==="GET_ALL_MANUFACTURERS"){
        return{
            ...state,
            manufacturers:action.manufacturers
        }
    } if(action.type==="GET_ALL_MODELS"){
        return{
            ...state,
            models:action.models
        }
    } if(action.type==="GET_ALL_MODEL_YEARS"){
        return{
            ...state,
            modelYears:action.modelYears
        }
    } if(action.type==="GET_ALL_CARS"){
        return{
            ...state,
            cars:action.cars
        }
    } if(action.type==="GET_ALL_CAR_IMAGES"){
        return{
            ...state,
            carImages:action.carImages
        }
    } if(action.type==="GET_ALL_HOTSPOT_LOCATIONS"){
        return{
            ...state,
            hotspotLocations:action.hotspotLocations
        }
    } if(action.type==="GET_ALL_HOTSPOT_DETAILS"){
        return{
            ...state,
            hotspotDetails:action.hotspotDetails
        }
    } else{
        return{
            ...state
        }
    }
}

export default mainReducer;


// export default function(state = defaultState, action) => {
//     switch (action.type) {
//       case 'CHANGE_COLOR':
//         return{
//             ...state,
//             color:action.color
//         };
//       case 'GET_MANUFACTURERS_PENDING':
//         // this means the call is pending in the browser and has not
//         // yet returned a response
//         // ...state
//       case 'GET_MANUFACTURERS_FULFILLED':
//         // this means the call is successful and the response has been set
//         // to action.payload
//         // ...state
//       case 'GET_MANUFACTURERS_REJECTED':
//         // this means the response was unsuccessful so you can handle that
//         // error here
//         // ...state
//       default:
//         return state;
//     }
//   }
