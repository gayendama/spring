INSERT INTO item_to_process (id, objet_id)
	SELECT
        public.uuid_generate_v4(),
        a.id
    FROM credit  a
        left outer join compte_type_pret b on a.nbre_jours_retard >= b.duree_min and a.nbre_jours_retard < b.duree_max and a.type_pret_id=b.type_pret_id and b.type='CS'
        left outer join tranche_type_souffrance c on a.ind_type_souffrance = c.ind_type_souffrance
        left outer join tranche_type_souffrance d on b.ind_type_souffrance = d.ind_type_souffrance
        left outer join compte_type_pret e on a.nbre_jours_retard >= e.duree_min and a.nbre_jours_retard < e.duree_max and a.type_pret_id=e.type_pret_id and e.type='IS'
    WHERE a.statut_credit in ('SO','VA','PX')
        and a.ind_type_souffrance <>  b.ind_type_souffrance
    ORDER BY a.numero_credit
on CONFLICT (objet_id) DO NOTHING;