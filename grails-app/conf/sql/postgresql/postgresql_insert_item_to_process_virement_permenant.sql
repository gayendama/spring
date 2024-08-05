insert into item_to_process (id, version, objet_id)
    select public.uuid_generate_v4(), 0, v.id
    from virement_permanent as v
    where
        v.deleted = false
        and v.statut_virement = :statut
        and v.date_fin_virement <= :dateTraitement
        and v.date_prochaine_tombee = :dateTraitement
        and v.nbre_de_jour_ins < v.nbre_de_jour_recherche_max
ON CONFLICT (objet_id) DO NOTHING;