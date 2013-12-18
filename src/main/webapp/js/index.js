$(document).ready(function() {
            $('#jquery').dataTable(
                {"bPaginate": false,
                    "bFilter": false,
                    "bInfo": false}
            );

            $('#grandsprix').dataTable(
                {"bPaginate": false,
                    "bFilter": false,
                    "bSort": false,
                    "bInfo": false}
            );

            addEvent(e$('grandsprix'), 'click', clickGp, false);

        } );

function clickGp(event) {
    var _tr = getParent(event).parentElement;
    ajax('/php/vues/controleur.php?action=ResultatsGrandPrix&championnat=1&grandprix='+_tr.id,{success:toto, params:{dropZone:'results'}});
    //ajax('http://localhost/gtr/php/vues/controleur.php?action=ResultatsGrandPrix&championnat=1&grandprix='+_tr.id,{success:toto, params:{dropZone:'results'}});
    //ajax('http://gtr.leludo.net/php/vues/controleur.php?action=ResultatsGrandPrix&championnat=1&grandprix='+_tr.id,{success:toto, params:{dropZone:'results'}});
}

function toto(json, params) {

    e$(params.dropZone).innerHTML = "";

    var _resultats = eval(json);

    var _table = document.createElement('table');
    var _thead = document.createElement('thead');
    var _tbody = document.createElement('tbody');
    var _trh= document.createElement('tr');
    _thead.appendChild(_trh);
    _table.appendChild(_thead);
    _table.appendChild(_tbody);

    var _th;
    _th = _trh.appendChild(document.createElement('th'));
    _th.textContent = 'Course';
    _th = _trh.appendChild(document.createElement('th'));
    _th.textContent = 'Place';
    _th = _trh.appendChild(document.createElement('th'));
    _th.textContent = 'Pole';
    _th = _trh.appendChild(document.createElement('th'));
    _th.textContent = 'Pilote';
    _th = _trh.appendChild(document.createElement('th'));
    _th.textContent = 'Points';

    for (var _i = 0; _i < _resultats.length ; _i++) {
        var _trb = _tbody.appendChild(document.createElement('tr'));
        var _td;
        _td = document.createElement('td');
        _td.textContent = _resultats[_i].numCourse;
        _trb.appendChild(_td);
        _td = document.createElement('td');
        _td.textContent = _resultats[_i].place;
        _trb.appendChild(_td);
        _td = document.createElement('td');
        if (_resultats[_i].pole == 1) _td.innerHTML = '<img src="../../design/Accept16.png" />';
        _trb.appendChild(_td);
        _td = document.createElement('td');
        _td.textContent = _resultats[_i].name;
        _trb.appendChild(_td);
        _td = document.createElement('td');
        _td.textContent = _resultats[_i].points;
        _trb.appendChild(_td);
    }

    e$(params.dropZone).appendChild(_table);
}