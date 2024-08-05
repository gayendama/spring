insert into item_to_process (id, objet_id)
select public.uuid_generate_v4(), a.id  from credit a
where a.statut_credit = :statuCredit
and (coalesce(a.montant_contentieux,0)- coalesce(a.montant_contentieux_paye, 0)) > 0
order by a.numero_credit
ON CONFLICT (objet_id) DO NOTHING