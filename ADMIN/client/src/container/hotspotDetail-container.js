import React from 'react';
import {connect} from "react-redux";
import * as actionCreators from "../actions/indexAction";
import HotspotDetail from "../component/hotspotDetail";

class HotspotDetailCon extends React.Component{
    render(){
        return(
            <HotspotDetail handleClick={this.props.loadHotspotDetails} hotspotDetails={this.props.hotspotDetails}></HotspotDetail>
        )
    }
}

const mapStateToProps=(state)=>{
    return state
};

export default connect (mapStateToProps, actionCreators)(HotspotDetailCon);