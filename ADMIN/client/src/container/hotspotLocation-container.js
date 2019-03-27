import React from 'react';
import {connect} from "react-redux";
import * as actionCreators from "../actions/indexAction";
import HotspotLocation from "../component/hotspotLocation";

class HotspotLocationCon extends React.Component{
    render(){
        return(
            <HotspotLocation handleClick={this.props.loadCarsHotspotLocations} 
                             handleRadioBtnCarId={(carId) => this.props.loadHotspotLocationsCarId(carId)}
                             carsHotspotLocations={this.props.carsHotspotLocations} 
                             hotspotLocations={this.props.hotspotLocations}
                             handleRadioBtnCarImageId={(carImageId) => this.props.loadHotspotLocationsCarImageId(carImageId)}
                             hotspotLocationsCarImages={this.props.hotspotLocationsCarImages}
                             >
            </HotspotLocation>
        )
    }
}

const mapStateToProps=(state)=>{
    return state
};

export default connect (mapStateToProps, actionCreators)(HotspotLocationCon);