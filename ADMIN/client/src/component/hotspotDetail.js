import React from "react";
import BootstrapTable from '../../node_modules/react-bootstrap-table-next';
import filterFactory, { textFilter } from '../../node_modules/react-bootstrap-table2-filter';
import paginationFactory from 'react-bootstrap-table2-paginator';


class HotspotDetail extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
          hotspotDetails: [],
            columns: [{
              dataField: 'hotspotDetailId',
              text: 'Hotspot Detail ID',
              sort: true
            },
            {
              dataField: 'uri',
              text: 'URL',
              sort: true
            },
            {
              dataField: 'notes',
              text: 'Notes',
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
        console.log(this.props.hotspotDetails.length)
        return (
            <div className="container" style={{ marginTop: 50 }}>
                <button onClick={()=>{this.props.handleClick()}}>Load Data</button>
                <p/>
                {this.props.hotspotDetails.length > 0 ? <BootstrapTable 
                    striped
                    hover
                    keyField='id' 
                    data={ this.props.hotspotDetails } 
                    columns={ this.state.columns }
                    filter={ filterFactory() }
                    pagination={ paginationFactory() }/> : null}
            </div>
        );
      }
}

export default HotspotDetail;