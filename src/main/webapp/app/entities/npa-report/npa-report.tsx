import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './npa-report.reducer';
import { INpaReport } from 'app/shared/model/npa-report.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface INpaReportProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const NpaReport = (props: INpaReportProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { npaReportList, match, loading } = props;
  return (
    <div>
      <h2 id="npa-report-heading">
        <Translate contentKey="myApp.npaReport.home.title">Npa Reports</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="myApp.npaReport.home.createLabel">Create new Npa Report</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {npaReportList && npaReportList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="myApp.npaReport.npaId">Npa Id</Translate>
                </th>
                <th>
                  <Translate contentKey="myApp.npaReport.location">Location</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {npaReportList.map((npaReport, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${npaReport.id}`} color="link" size="sm">
                      {npaReport.id}
                    </Button>
                  </td>
                  <td>{npaReport.npaId}</td>
                  <td>{npaReport.location}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${npaReport.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${npaReport.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${npaReport.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="myApp.npaReport.home.notFound">No Npa Reports found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ npaReport }: IRootState) => ({
  npaReportList: npaReport.entities,
  loading: npaReport.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(NpaReport);
