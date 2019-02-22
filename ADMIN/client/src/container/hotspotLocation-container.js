import React from 'react';
import {connect} from "react-redux";
import * as actionCreators from "../actions/indexAction";
import HotspotLocation from "../component/hotspotLocation";

class HotspotLocationCon extends React.Component{
    render(){
        return(
            <HotspotLocation handleClick={this.props.loadHotspotLocations} hotspotLocations={this.props.hotspotLocations}></HotspotLocation>
        )
    }
}

const mapStateToProps=(state)=>{
    return state
};

export default connect (mapStateToProps, actionCreators)(HotspotLocationCon);