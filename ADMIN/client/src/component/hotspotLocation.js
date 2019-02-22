import React from "react";
import BootstrapTable from '../../node_modules/react-bootstrap-table-next';
import filterFactory, { textFilter } from '../../node_modules/react-bootstrap-table2-filter';
import paginationFactory from 'react-bootstrap-table2-paginator';


class HotspotLocation extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
          hotspotLocations: [],
            columns: [{
              dataField: 'hotspotId',
              text: 'Hotspot ID',
              sort: true
            },
            {
              dataField: 'xLoc',
              text: 'X',
              sort: true
            },
            {
              dataField: 'yLoc',
              text: 'Y',
              sort: true
            },
            {
              dataField: 'hotspotDesc',
              text: 'Description',
              sort: true,
              filter: textFilter()
            },
            {
              dataField: 'active',
              text: 'Active',
              sort: true
            }]
          }
    }
      render() {
        console.log(this.props);
        // console.log(arr.length)
        console.log(this.props.hotspotLocations.length)
        return (
            <div className="container" style={{ marginTop: 50 }}>
                <button onClick={()=>{this.props.handleClick()}}>Load Data</button>
                <p/>
                {this.props.hotspotLocations.length > 0 ? <BootstrapTable 
                    striped
                    hover
                    keyField='id' 
                    data={ this.props.hotspotLocations } 
                    columns={ this.state.columns }
                    filter={ filterFactory() }
                    pagination={ paginationFactory() }/> : null}
            </div>
        );
      }
}

export default HotspotLocation;