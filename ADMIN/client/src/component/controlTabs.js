import React from 'react';
import {Tabs, Tab} from 'react-bootstrap';
import Manufacturercon from '../container/manufacturer-container';
import Modelcon from '../container/model-container';
import ModelYearcon from '../container/modelYear-container';
import Carcon from '../container/car-container';
import CarImagecon from '../container/carImage-container';
import HotspotLocationcon from '../container/hotspotLocation-container';
import HotspotDetailcon from '../container/hotspotDetail-container';

class ControlledTabs extends React.Component {
    constructor(props, context) {
      super(props, context);
      this.state = {
        key: 'home',
      };
    }
    render() {
        return (
          <Tabs id="controlled-tab-example"
                activeKey={this.state.key}
                onSelect={key => this.setState({ key })}>
                        <Tab eventKey="manufacturer" title="Manufacturer">
                            <Manufacturercon/>
                        </Tab>
                        <Tab eventKey="model" title="Model">
                            <Modelcon/>
                        </Tab>
                        <Tab eventKey="modelYear" title="Model Year">
                            <ModelYearcon/>
                        </Tab>
                        <Tab eventKey="car" title="Car">
                            <Carcon/>
                        </Tab>
                        <Tab eventKey="carImages" title="Car Images">
                            <CarImagecon/>
                        </Tab>
                        <Tab eventKey="hotspotLoc" title="Hotspot Locations">
                            <HotspotLocationcon/>
                        </Tab>
                        <Tab eventKey="hotspotDet" title="Hotspot Details">
                            <HotspotDetailcon/>
                        </Tab>
            </Tabs>
        );
      }
    }
    
    export default ControlledTabs;